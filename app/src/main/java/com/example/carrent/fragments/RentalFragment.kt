package com.example.carrent.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrent.MyAdapter
import com.example.carrent.R


class RentalFragment : Fragment(R.layout.fragment_rental), MyAdapter.OnItemClickListener {

    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var parkingArrayList: ArrayList<com.example.carrent.List>



    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>
    lateinit var status: Array<String>

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(parkingArrayList, this)
        recyclerView.adapter = adapter



    }

    override fun onItemClick(position: Int) {
        val clickedItem = parkingArrayList[position]
        clickedItem.status = "  დაკავებულია  "
        adapter.notifyItemChanged(position)
        val spotNameResult = clickedItem.heading
        val num="1"
        val spotImage=clickedItem.titleImage

        val intent = RentalFragmentDirections.actionRentalFragmentToTimeFragment(spotNameResult,spotImage,num)
        findNavController().navigate(intent)

    }

    private fun dataInitialize() {
        parkingArrayList = arrayListOf<com.example.carrent.List>()
        imageId = arrayOf(
            R.drawable.ic_wheelchair,
            R.drawable.ic_wheelchair,
            R.drawable.ic_wheelchair,
            R.drawable.ic_wheelchair,
            R.drawable.ic_wheelchair,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,
            R.drawable.ic_car,


            )

        heading = arrayOf(
            getString(R.string.park_1),
            getString(R.string.park_2),
            getString(R.string.park_3),
            getString(R.string.park_4),
            getString(R.string.park_5),
            getString(R.string.park_6),
            getString(R.string.park_7),
            getString(R.string.park_8),
            getString(R.string.park_9),
            getString(R.string.park_10),
            getString(R.string.park_11),
            getString(R.string.park_12),
            getString(R.string.park_13),
            getString(R.string.park_14),
            getString(R.string.park_15),
            getString(R.string.park_16),
            getString(R.string.park_17),
            getString(R.string.park_18),
            getString(R.string.park_19),
            getString(R.string.park_20),
            getString(R.string.park_21),
            getString(R.string.park_22),
            getString(R.string.park_23),
            getString(R.string.park_24),
            getString(R.string.park_25),
            getString(R.string.park_26),
            getString(R.string.park_27),
            getString(R.string.park_28),
            getString(R.string.park_29),
            getString(R.string.park_30),
            getString(R.string.park_1_C),
            getString(R.string.park_2_C),
            getString(R.string.park_3_C),
            getString(R.string.park_4_C),
            getString(R.string.park_5_C),
        )

        status = arrayOf(
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",
            "  თავისუფალია  ",


            )

        for (i in imageId.indices) {
            val list = com.example.carrent.List(imageId[i], heading[i], status[i])
            parkingArrayList.add(list)
        }


    }
}