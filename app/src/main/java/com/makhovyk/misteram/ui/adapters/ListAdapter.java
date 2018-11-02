package com.makhovyk.misteram.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makhovyk.misteram.R;
import com.makhovyk.misteram.Utils.Utils;
import com.makhovyk.misteram.data.model.Order;
import com.makhovyk.misteram.data.model.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.TaskHolder> {

    private List<Task> tasks = new ArrayList<Task>();
    private Context context;

    public ListAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_task, viewGroup, false);
        return new TaskHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder viewHolder, int i) {
        viewHolder.bindOrder(tasks.get(i));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_company_order_time)
        TextView tvCompanyOrderTime;
        @BindView(R.id.tv_company_order_id)
        TextView tvCompanyOrderId;
        @BindView(R.id.tv_company_order_address_title)
        TextView tvCompanyOrderAddressTitle;
        @BindView(R.id.tv_company_order_address_description)
        TextView tvCompanyOrderAddressDescription;
        @BindView(R.id.tv_company_order_amount)
        TextView tvCompanyOrderAmount;
        @BindView(R.id.ll_company_order_tags)
        LinearLayout llCompanyOrderTags;

        @BindView(R.id.tv_user_order_time)
        TextView tvUserOrderTime;
        @BindView(R.id.tv_user_order_id)
        TextView tvUserOrderId;
        @BindView(R.id.tv_user_order_address_title)
        TextView tvUserOrderAddressTitle;
        @BindView(R.id.tv_user_order_address_description)
        TextView tvUserOrderAddressDescription;
        @BindView(R.id.tv_user_order_amount)
        TextView tvUserOrderAmount;
        @BindView(R.id.ll_user_order_tags)
        LinearLayout llUserOrderTags;

        @BindView(R.id.tv_task_status)
        TextView tvTaskStatus;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindOrder(Task task) {

            // handling receiving order from company
            Order companyOrder = task.getOrders().get(0);
            String companyTimeString = DateFormat.format("hh:mm", companyOrder.getTime()).toString();
            String companyOrderId = String.valueOf(companyOrder.getId());
            companyOrderId = companyOrderId.substring(companyOrderId.length() - 4);
            tvCompanyOrderTime.setText(companyTimeString);
            tvCompanyOrderId.setText(companyOrderId);
            tvCompanyOrderAddressTitle.setText(companyOrder.getAddress().getTitle());
            tvCompanyOrderAddressDescription.setText(companyOrder.getAddress().getDescription());
            tvCompanyOrderAmount.setText(context.getString(R.string.amount, String.valueOf(companyOrder.getAmount())));
            setTextColorDependingOnAmount(tvCompanyOrderAmount, companyOrder.getAmount());
            addTagsImages(companyOrder.getTags(), llCompanyOrderTags);

            // handling delivering order from user
            Order userOrder = task.getOrders().get(1);
            String userTimeString = DateFormat.format("hh:mm", userOrder.getTime()).toString();
            String userOrderId = String.valueOf(userOrder.getId());
            userOrderId = userOrderId.substring(userOrderId.length() - 4);
            tvUserOrderTime.setText(userTimeString);
            tvUserOrderId.setText(userOrderId);
            tvUserOrderAddressTitle.setText(userOrder.getAddress().getTitle());
            tvUserOrderAddressDescription.setText(userOrder.getAddress().getDescription());
            tvUserOrderAmount.setText(context.getString(R.string.amount, String.valueOf(userOrder.getAmount())));
            setTextColorDependingOnAmount(tvUserOrderAmount, userOrder.getAmount());
            addTagsImages(userOrder.getTags(), llUserOrderTags);

            tvTaskStatus.setText(context.getString(R.string.task_status));
        }

        private void setTextColorDependingOnAmount(TextView tv, double amount) {
            if (amount < 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            } else if (amount > 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
            }
        }

        private void addTagsImages(List<String> tags, LinearLayout ll) {

            // dynamically adding tags images
            ll.removeAllViews();
            for (String tag : tags) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(Utils.getTagDrawableResource(tag));
                imageView.setPadding(10, 10, 10, 10);
                ll.addView(imageView);
            }
        }
    }
}
