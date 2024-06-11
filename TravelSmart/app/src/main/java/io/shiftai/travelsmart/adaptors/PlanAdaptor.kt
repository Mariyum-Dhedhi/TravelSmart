package io.shiftai.travelsmart.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.shiftai.travelsmart.data.Day
import io.shiftai.travelsmart.databinding.DaysCardBinding


class PlanAdaptor: RecyclerView.Adapter<PlanAdaptor.DayViewHolder>() {
    inner class DayViewHolder(private val binding: DaysCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Day) {
            binding.apply {
                dayNum.text = "Day " + day.formattedNum()
                description.text = day.desc
            }
        }
    }

        private val diffCallback = object : DiffUtil.ItemCallback<Day>() {
            override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem.num == newItem.num

            }

            override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem == newItem
            }

        }

        val differ = AsyncListDiffer(this, diffCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
            return DayViewHolder(
                DaysCardBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }

        override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
            val day = differ.currentList[position]
            holder.bind(day)

            holder.itemView.setOnClickListener {
                onClick?.invoke(day)
            }
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        var onClick: ((Day) -> Unit)? = null
}