package com.example.simulacion01dlassallec

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.simulacion01dlassallec.viewModel.ProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class SecondFragment : Fragment() {

    private val myViewModel: ProductViewModel by activityViewModels()
    private var idProducto = 0
    lateinit var productName: String


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel.productSelection.observe(viewLifecycleOwner, Observer {
            idProducto = it

            myViewModel.getProductDetails(idProducto).observe(viewLifecycleOwner, Observer {

                it?.let {
                    val imageProdSel = view.findViewById<ImageView>(R.id.imgSelected)
                    Glide.with(this).load(it.image).fitCenter().into(imageProdSel)

                    val nameSelected = view.findViewById<TextView>(R.id.nameSelected)
                    val priceSelected = view.findViewById<TextView>(R.id.priceSelected)
                    val lastPriceSelected = view.findViewById<TextView>(R.id.lastPriceSelected)
                    val description = view.findViewById<TextView>(R.id.tvdescription)

                    nameSelected.text = it.name
                    priceSelected.text = getString(R.string.price) + " " + it.price.toString()
                    lastPriceSelected.text = getString(R.string.lastprice) + " " + it.lastPrice.toString()
                    description.text = it.description

                    productName = it.name

                }
            })
        })

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            val mailAddress = getString(R.string.mail_address)
            val mailSubject = getString(R.string.mail_subject) + " " + productName + " Id $idProducto"
            val mailText = getString(R.string.mail_text1) + " " + productName + " " + getString(R.string.mail_text2)
            createEmail(mailAddress, mailSubject, mailText)

        }

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

        }
    }

    fun createEmail(mailAddress: String, mailSubject: String, mailText: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(mailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, mailSubject)
        intent.putExtra(Intent.EXTRA_TEXT, mailText)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "e.message", Toast.LENGTH_LONG).show()
        }

    }
}