package com.harshit.merchant

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter(private val customerList: List<CustomerModel>) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLoanId: TextView = itemView.findViewById(R.id.tvLoanId)
        val tvCustomerName: TextView = itemView.findViewById(R.id.tvCustomerName)
        val tvCustomerPhone: TextView = itemView.findViewById(R.id.tvCustomerPhone)
        val tvImei: TextView = itemView.findViewById(R.id.tvImei)
        val tvSyncDate: TextView = itemView.findViewById(R.id.tvSyncDate)
        val tvRemarks: TextView = itemView.findViewById(R.id.tvRemarks)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnOpenControl: Button = itemView.findViewById(R.id.btnOpenControl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer_card, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customerList[position]

        holder.tvLoanId.text = "Loan ID: ${customer.loanId}"
        holder.tvCustomerName.text = "👤 ${customer.name}"
        holder.tvCustomerPhone.text = "📞 ${customer.mobile}"
        holder.tvImei.text = "📱 IMEI: ${customer.imei}"
        holder.tvSyncDate.text = "📅 ${customer.syncDate}"
        holder.tvRemarks.text = "Remarks: ${customer.remarks}"

        holder.tvStatus.text = "Status: ${customer.status}"
        if (customer.status == "Locked" || customer.isDeviceLocked) {
            holder.tvStatus.text = "Status: Locked"
            holder.tvStatus.setTextColor(Color.parseColor("#D32F2F"))
        } else {
            holder.tvStatus.text = "Status: Unlocked"
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"))
        }

        holder.btnOpenControl.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CustomerDetailControlActivity::class.java)
            intent.putExtra("CUSTOMER_IMEI", customer.imei)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
}
