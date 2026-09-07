package com.harshit.merchant

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustomerListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyMessage: TextView
    private lateinit var customerAdapter: CustomerAdapter
    private val customerList = ArrayList<CustomerModel>()
    private val databaseRef = FirebaseDatabase.getInstance().getReference("Customers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        recyclerView = findViewById(R.id.recyclerViewCustomers)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        val btnAddDemo = findViewById<Button>(R.id.btnAddDemo)

        recyclerView.layoutManager = LinearLayoutManager(this)
        customerAdapter = CustomerAdapter(customerList)
        recyclerView.adapter = customerAdapter

        fetchCustomersFromFirebase()

        btnAddDemo.setOnClickListener {
            val demoImei = "8621440820" + (1000..9999).random()
            val demoCustomer = CustomerModel(
                loanId = "163659925",
                imei = demoImei,
                name = "Priyanka Test User",
                mobile = "8171430180",
                email = "vk2605959@gmail.com",
                remarks = "Demo Customer",
                syncDate = "07 Sep 2026",
                status = "Unlocked"
            )

            databaseRef.child(demoImei).setValue(demoCustomer).addOnSuccessListener {
                Toast.makeText(this, "Demo Customer Added!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCustomersFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                customerList.clear()
                for (data in snapshot.children) {
                    val customer = data.getValue(CustomerModel::class.java)
                    if (customer != null) {
                        customerList.add(customer)
                    }
                }

                customerAdapter.notifyDataSetChanged()

                if (customerList.isEmpty()) {
                    tvEmptyMessage.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvEmptyMessage.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CustomerListActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

