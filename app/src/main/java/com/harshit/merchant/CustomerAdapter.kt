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
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnOpenControl: Button = itemView.findViewById(R.id.btnOpenControl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer_card, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customerList[position]

        // UI में डेटा सेट करना
        holder.tvLoanId.text = "Loan ID: ${customer.loanId}"
        holder.tvCustomerName.text = "👤 ${customer.name}"
        holder.tvCustomerPhone.text = "📞 ${customer.mobile}"
        holder.tvImei.text = "📱 IMEI: ${customer.imei}"
        
        // Status कलर
        holder.tvStatus.text = "Status: ${customer.status}"
        if (customer.status == "Locked") {
            holder.tvStatus.setTextColor(Color.parseColor("#D32F2F")) // Red
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50")) // Green
        }

        // Control Panel पर क्लिक करने का लॉजिक
        holder.btnOpenControl.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CustomerDetailControlActivity::class.java)
            intent.putExtra("CUSTOMER_IMEI", customer.imei) // यहाँ असली IMEI पास हो रहा है
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
}
