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
    
    // फायरबेस रेफरेंस
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

        // 1. फायरबेस से लाइव लिस्ट लाना
        fetchCustomersFromFirebase()

        // 2. टेस्टिंग के लिए ऑटो-ऐड डेमो बटन का लॉजिक
        btnAddDemo.setOnClickListener {
            val randomImei = "8621440820" + (1000..9999).random() // कोई भी रैंडम IMEI
            
            val demoCustomer = CustomerModel(
                loanId = "163659925",
                imei = randomImei,
                name = "Test Demo User",
                mobile = "9876543210",
                email = "demo@gmail.com",
                status = "Unlocked"
            )

            // फायरबेस में सेव करना
            databaseRef.child(randomImei).setValue(demoCustomer).addOnSuccessListener {
                Toast.makeText(this, "डेमो कस्टमर ऐड हो गया!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCustomersFromFirebase() {
        // ValueEventListener का फायदा यह है कि जब कोई कस्टमर रिमूव होगा तो लिस्ट अपने आप अपडेट हो जाएगी
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                customerList.clear() // पुरानी लिस्ट साफ़ करो

                for (data in snapshot.children) {
                    val customer = data.getValue(CustomerModel::class.java)
                    if (customer != null) {
                        customerList.add(customer)
                    }
                }

                customerAdapter.notifyDataSetChanged()

                // अगर लिस्ट खाली है तो Empty Message दिखाओ, नहीं तो RecyclerView दिखाओ
                if (customerList.isEmpty()) {
                    tvEmptyMessage.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvEmptyMessage.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CustomerListActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
