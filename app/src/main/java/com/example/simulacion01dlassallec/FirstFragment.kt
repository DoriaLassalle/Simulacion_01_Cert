package com.example.simulacion01dlassallec

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simulacion01dlassallec.model.Product
import com.example.simulacion01dlassallec.viewModel.ProductViewModel

class FirstFragment : Fragment(), ProductAdapter.PassProductData {

    private val myViewModel: ProductViewModel by activityViewModels()
    private lateinit var myAdapter:ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter= ProductAdapter(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView=view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager= GridLayoutManager(context, 2)
        recyclerView.adapter=myAdapter

        myViewModel.allProducts.observe(viewLifecycleOwner, Observer {

            it?.let {
                Log.d("productos", it.toString())
                myAdapter.updateAdapter(it)
            }
        })

        myViewModel.getProductDetails(1).observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("detalle", it.toString())
            }
        })

    }

    override fun passProductInfo(product: Product) {
        val productSelectedId=product.id

        myViewModel.productSelected(productSelectedId)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}