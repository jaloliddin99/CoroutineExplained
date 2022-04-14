package com.jaloliddinabdullaev.coroutineexplained.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.ImageItem
import com.jaloliddinabdullaev.coroutineexplained.databinding.LayoutMainMemeItemBinding

class AnimalsAdapter(val listener: OnImageCLickListener) :
    ListAdapter<ImageItem, AnimalsAdapter.RestaurantViewHolder>(RestaurantComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            LayoutMainMemeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class RestaurantViewHolder(private val binding: LayoutMainMemeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: ImageItem) {
            binding.apply {

                Glide.with(itemView)
                    .load(restaurant.image)
                    .into(imageView)

                imageView.transitionName="hello1$adapterPosition"
                imageView.setOnClickListener {
                    listener.onImageClick(restaurant.image, "hello1$adapterPosition", imageView)
                }
            }
        }
    }

    class RestaurantComparator : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem) =
            oldItem == newItem
    }

    interface OnImageCLickListener{
        fun onImageClick(imageUrl:String, imageId:String, imageView: AppCompatImageView)
    }
}