package com.ikresimir.mbanking.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ikresimir.mbanking.R
import com.ikresimir.mbanking.model.Transaction
import java.math.RoundingMode
import java.text.DecimalFormat

class RecyclerViewAdapter(val transactionList: List<Transaction>, val currency: String) :
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTransactionDescription: TextView =
            itemView.findViewById(R.id.txtTransactionDescription)
        val txtTransactionDate: TextView = itemView.findViewById(R.id.txtTransactionDate)
        val txtTransactionType: TextView = itemView.findViewById(R.id.txtTransactionType)
        val txtTransactionAmount: TextView = itemView.findViewById(R.id.txtTransactionAmount)
        val lblCurrency: TextView = itemView.findViewById(R.id.lblCurrency)
        val ivTransactionIcon: ImageView = itemView.findViewById(R.id.transactionIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentTransaction = transactionList[position]

        holder.txtTransactionDescription.text = currentTransaction.description
        holder.txtTransactionDate.text = currentTransaction.date
        holder.txtTransactionAmount.text = formatAmount(currentTransaction.amount)
        holder.lblCurrency.text = currency

        //Check if transaction type exists in API response and update accordingly
        if (currentTransaction.type == "") {
            holder.txtTransactionType.text = "TRANSFER"
            holder.ivTransactionIcon.setImageResource(R.drawable.ic_coins)
        } else {
            holder.txtTransactionType.text = "${currentTransaction.type}"
            when (currentTransaction.type) {
                "GSM VOUCHER" -> holder.ivTransactionIcon.setImageResource(R.drawable.ic_gsm_voucher)
                "EXCHANGE" -> holder.ivTransactionIcon.setImageResource(R.drawable.ic_exchange)
                else -> holder.ivTransactionIcon.setImageResource(R.drawable.ic_coins)
            }
        }
    }

    fun formatAmount(number: Double): String {
        val df = DecimalFormat("00.00")
        df.roundingMode = RoundingMode.CEILING
        var formattedAmount = df.format(number)
        formattedAmount = formattedAmount.replace('.', ',')
        return formattedAmount
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}