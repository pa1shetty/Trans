package com.example.trans.screens.bottomsheet

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.RequestManager
import com.example.trans.R
import com.example.trans.data.module.CartData
import com.example.trans.databinding.BottomShhetAddToCartBinding
import com.example.trans.utillity.UtilsClassUI
import com.example.trans.utillity.firebase.FireBaseConfigRepository
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class BottomSheet @Inject constructor(
    @ActivityContext private val context: Context,
    private val glide: RequestManager,
    private val fireBaseConfigRepository: FireBaseConfigRepository
) {
    @Inject
    lateinit var utilsClassUI: UtilsClassUI
    fun bottomSheetAddToCart(
        cartData: CartData,
        addProductToCart: (CartData) -> Unit,
        removeProductFromCart: (Int) -> Unit
    ) {
        val bottomSheetDialog =
            BottomSheetDialogCustom(
                context as Activity
            )
        val bottomSheetDialogueBinding =
            BottomShhetAddToCartBinding.inflate(context.layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetDialogueBinding.root)
        bottomSheetDialog.show()
        val prodQty: MutableLiveData<Int> = MutableLiveData()

        prodQty.value = if (cartData.prdQnty < cartData.prdMinOrderQnty) {
            cartData.prdMinOrderQnty.toInt()
        } else {
            cartData.prdQnty
        }
        bottomSheetDialogueBinding.tvPrdName.text = cartData.prdName
        glide.load(cartData.prdImage).into(bottomSheetDialogueBinding.ivPrd)
        bottomSheetDialogueBinding.tvRemovePrd.setOnClickListener {
            val currentValue = prodQty.value!!
            if (currentValue > cartData.prdMinOrderQnty.toInt()) {
                prodQty.value = currentValue - 1
            } else {
                utilsClassUI.toastMessage("Minimum Allowed Quantity is ${cartData.prdMinOrderQnty}")
            }
        }

        bottomSheetDialogueBinding.tvAddPrd.setOnClickListener {
            val currentValue = prodQty.value!!
            if (currentValue < cartData.prdMaxOrderQnty) {
                prodQty.value = currentValue + 1
            } else {
                utilsClassUI.toastMessage("Maximum Allowed Quantity is ${cartData.prdMaxOrderQnty}")
            }
        }
        prodQty.observe(context as FragmentActivity) { value ->
            bottomSheetDialogueBinding.tvPrdCount.text = value.toString()
            bottomSheetDialogueBinding.tvAddToCart.text =
                context.getString(R.string.add_items_to_cart, (value*cartData.prdAmount).toString())
        }

        bottomSheetDialogueBinding.btnAddToCart.setOnClickListener {
            val currentValue = prodQty.value!!
            cartData.prdQnty = currentValue
            addProductToCart(cartData)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialogueBinding.mcDelete.setOnClickListener {
            removeProductFromCart(cartData.prdId)
            bottomSheetDialog.dismiss()
        }
    }
}