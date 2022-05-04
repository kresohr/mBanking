package com.ikresimir.mbanking.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikresimir.mbanking.R
import com.ikresimir.mbanking.model.Account
import com.ikresimir.mbanking.model.Transaction

class ViewPagerAdapter(
    val accountList: List<Account>,
    val transactionList: List<Transaction>,
    val activity: Activity
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAccountNumber: TextView = itemView.findViewById(R.id.txtAccountNumber)
        val txtAccountBalance: TextView = itemView.findViewById(R.id.txtAccountBalance)
        val txtBalanceCurrency: TextView = itemView.findViewById(R.id.txtBalanceCurrency)
        val btnSort: ImageView = itemView.findViewById(R.id.btnSort)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accountList.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentAccount = accountList[position]
        var filteredTransactionList = mutableListOf<Transaction>()
        holder.txtAccountNumber.text = currentAccount.iban
        holder.txtAccountBalance.text = currentAccount.amount
        holder.txtBalanceCurrency.text = currentAccount.currency

        //Check which transactions belong to account on current page
        for (transaction in transactionList) {
            if (transaction.accountId == currentAccount.id) {
                filteredTransactionList.add(transaction)
            }
        }
        var recyclerViewAdapter =
            RecyclerViewAdapter(filteredTransactionList, currentAccount.currency)
        var linearLayoutManager = LinearLayoutManager(activity)
        holder.recyclerView.layoutManager = linearLayoutManager
        holder.recyclerView.adapter = recyclerViewAdapter

        //SORTING when user selects button
        holder.btnSort.setOnClickListener {
            if (ascending) {
                filteredTransactionList =
                    filteredTransactionList.sortedBy { it.amount } as MutableList<Transaction>
                recyclerViewAdapter =
                    RecyclerViewAdapter(filteredTransactionList, currentAccount.currency)
                linearLayoutManager = LinearLayoutManager(activity)
                holder.recyclerView.layoutManager = linearLayoutManager
                holder.recyclerView.adapter = recyclerViewAdapter
                sortAscending()
            } else {
                filteredTransactionList =
                    filteredTransactionList.sortedByDescending { it.amount } as MutableList<Transaction>
                recyclerViewAdapter =
                    RecyclerViewAdapter(filteredTransactionList, currentAccount.currency)
                linearLayoutManager = LinearLayoutManager(activity)
                holder.recyclerView.layoutManager = linearLayoutManager
                holder.recyclerView.adapter = recyclerViewAdapter
                sortDescending()
            }

        }
    }

    fun sortAscending() {
        ascending = false
    }

    fun sortDescending() {
        ascending = true
    }

    companion object {
        var ascending = true
    }


}