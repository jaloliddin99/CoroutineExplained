package com.jaloliddinabdullaev.coroutineexplained.ui.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jaloliddinabdullaev.coroutineexplained.databinding.FragmentServiceBinding

private const val TAG = "FragmentService"

class FragmentService : Fragment() {

    private var _binding : FragmentServiceBinding?=null
    private val binding get() = requireNotNull(_binding)
    private lateinit var viewModel: FragmentViewModel
    private var service: MyService?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toggleUpdates.setOnClickListener {
                toggleUpdate()
            }
        }

        setObserver()

    }

    private fun setObserver(){
        viewModel.getBinder().observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated Connected to service")
            service = it?.service
        }
        viewModel.getIsProgressBarUpdating().observe(viewLifecycleOwner){

            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                @SuppressLint("SetTextI18n")
                override fun run() {
                    if (it==true){
                        if (viewModel.getBinder().value!=null){
                            if (service?.getProgress() == service?.getMaxValue()){
                                viewModel.setIsProgressBarUpdating(false)
                            }
                            binding.progresssBar.progress = service?.getProgress()!!
                            binding.progresssBar.max = service?.getMaxValue()!!
                            val stringValue=100*service?.getProgress()!!/service?.getMaxValue()!!
                            binding.textView.text = "$stringValue%"
                        }
                        handler.postDelayed(this, 100)
                    } else {
                        handler.removeCallbacks(this)
                    }
                }
            }
            if (it==true){
                binding.toggleUpdates.text = "Pause"
                handler.postDelayed(runnable, 100)
            }else{
                if (service?.getProgress() == service?.getMaxValue()){
                    binding.toggleUpdates.text = "Restart"
                }else{
                    binding.toggleUpdates.text = "Start"

                }
            }
        }
    }
    private fun toggleUpdate(){
        service?.let {
            if (it.getProgress()  == it.getMaxValue()){
                it.resetTask()
                binding.toggleUpdates.text = "Start"
            }else{

                if (it.getIsPaused() == true){
                    it.unpausePretendedWork()
                    viewModel.setIsProgressBarUpdating(isUpdating = true)
                }else{
                    Log.d(TAG, "toggleUpdatedawwwdawdawd ${it.getProgress()}")
                    it.pausePretendedLongWork()
                    viewModel.setIsProgressBarUpdating(isUpdating = false)
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        if (viewModel.getBinder()!=null){
            requireContext().unbindService(viewModel.getServiceConnection())
        }
    }
    override fun onResume() {
        super.onResume()
        startService()
    }


    private fun startService(){
        val intent=Intent(requireContext(), MyService::class.java)
        context?.startService(intent)
        bindService()
    }


    private fun bindService(){
        val intent=Intent(requireContext(), MyService::class.java)
        requireContext().bindService(intent, viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE)
    }


}