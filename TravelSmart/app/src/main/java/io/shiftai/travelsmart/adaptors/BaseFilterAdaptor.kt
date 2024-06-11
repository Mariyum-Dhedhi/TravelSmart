package io.shiftai.travelsmart.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.shiftai.travelsmart.data.Plan
import io.shiftai.travelsmart.databinding.PlanCardBinding


class BaseFilterAdaptor: RecyclerView.Adapter<BaseFilterAdaptor.BaseFilterViewHolder>() {
    inner class BaseFilterViewHolder(private val binding: PlanCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan) {
            binding.apply {
                Glide.with(itemView).load(plan.images[0]).into(imgPlan)
                title.text = plan.planTitle
                destination.text = plan.destination
                daysNum.text = plan.formattedNumOfDays() + " Days"
                budget.text = "Rs. " + plan.formattedBudget()
            }
        }
    }

        private val diffCallback = object : DiffUtil.ItemCallback<Plan>() {
            override fun areItemsTheSame(oldItem: Plan, newItem: Plan): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: Plan, newItem: Plan): Boolean {
                return oldItem == newItem
            }

        }

        val differ = AsyncListDiffer(this, diffCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFilterViewHolder {
            return BaseFilterViewHolder(
                PlanCardBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }

        override fun onBindViewHolder(holder: BaseFilterViewHolder, position: Int) {
            val plan = differ.currentList[position]
            holder.bind(plan)

            holder.itemView.setOnClickListener {
                onClick?.invoke(plan)
            }
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        var onClick: ((Plan) -> Unit)? = null
}