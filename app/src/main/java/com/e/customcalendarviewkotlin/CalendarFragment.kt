package com.e.customcalendarviewkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private var month: Calendar? = null
    private var itemMonth: Calendar? = null
    private var adapter: CalendarViewAdapter? = null
    private var handler: Handler? = null
    private var items: ArrayList<String>? = null


    @SuppressLint("SimpleDateFormat")
    private var calendarUpdater: Runnable = Runnable {
        items!!.clear()
        val df = SimpleDateFormat("yyyy/MM/dd")
        val cal = Calendar.getInstance()

        //currentMonth
        cal.get(Calendar.MONTH)
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val maxDay1 = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until maxDay1) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1)
                items!!.add(df.format(cal.time))
        }
        // Add next month
        cal[Calendar.MONTH] = cal[Calendar.MONTH] + 1
        cal.set(Calendar.DAY_OF_MONTH, 1)
        val maxDay2 = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until maxDay2) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1)
            items!!.add(df.format(cal.time))
        }

        adapter!!.setItems(items!!)
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        month = Calendar.getInstance()
        itemMonth = month!!.clone() as Calendar
        items = ArrayList()
        adapter = CalendarViewAdapter(activity!!, month as GregorianCalendar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.calendarRecyclerView) as RecyclerView
        recyclerView.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        handler = Handler()
        handler!!.post(calendarUpdater)
        return view
    }
}

