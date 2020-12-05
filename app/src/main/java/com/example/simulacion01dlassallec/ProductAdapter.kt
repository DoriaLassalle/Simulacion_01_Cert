package com.example.simulacion01dlassallec

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simulacion01dlassallec.model.Product


class ProductAdapter(val callback: FirstFragment): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList= emptyList<Product>()
    fun updateAdapter(mylist:List<Product>){
        productList=mylist
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val imageProd=itemview.findViewById<ImageView>(R.id.ivProduct)
        val nameProd=itemview.findViewById<TextView>(R.id.name)
        val priceProd=itemview.findViewById<TextView>(R.id.price)
        val click=itemview.setOnClickListener {
            callback.passProductInfo(productList[adapterPosition])

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.productolist_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {

        val img=productList[position].image
        Glide.with(holder.itemView.context).load(img).fitCenter().into(holder.imageProd)
        holder.nameProd.text=productList[position].name
        holder.priceProd.text=productList[position].price.toString()

    }

    override fun getItemCount()=productList.size

    interface PassProductData{
        fun passProductInfo(product: Product)
    }

}