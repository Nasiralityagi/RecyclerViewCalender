package com.e.customcalendarviewkotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.calender_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CalendarViewAdapter(private val mContext: Context, monthCalendar: GregorianCalendar)
    : RecyclerView.Adapter<CalendarViewAdapter.NoteViewHolder>() {
    var noteFilterList: ArrayList<String> = ArrayList()
    var context: Context? = null
    private var size: Int = 0

    fun setSize(size: Int){
        this.size = size
    }
    fun setItems(items: ArrayList<String>) {
        for (i in 0 until items.size) {
            val sdfmt1 = SimpleDateFormat("yyyy/MM/dd");
            val dDate = sdfmt1.parse(items[i]);
//            val formatter: DateFormat = SimpleDateFormat("yyyy/MM/dd")
            val cur = sdfmt1.format(Date())
            val curDate = sdfmt1.parse(cur)
            if (dDate!! > curDate) {
                Log.v("Itemssate", "Okay" + items[i])
                noteFilterList.add(items[i])
            }

        }
        Log.v("ItemDate", "" + items)

//        this.noteFilterList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.calender_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val dateParts = noteFilterList[position].split("/")
        Log.v("modb", "   " + dateParts)
        var day: String? = null
        var month: String? = null
        val year = dateParts[0]
        if (dateParts[1].startsWith("0")) {
            month = dateParts[1].replaceFirst("0", "")
        } else {
            month = dateParts[1]
        }
        if (dateParts[2].startsWith("0")) {
            day = dateParts[2].replaceFirst("0", "")
        } else {
            day = dateParts[2]
        }
        val separatedTime = noteFilterList[position].split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        // taking last part of date. ie; 2 from 2012-12-02
        val gridvalue = separatedTime[2].replaceFirst("^0*".toRegex(), "")
        val calendar: Calendar = GregorianCalendar(year.toInt(), month.toInt() - 1, day.toInt()) // Note that Month value is 0-based. e.g., 0 for January.
        val result = calendar[Calendar.DAY_OF_WEEK]
        var dateDay = ""
        when (result) {
            Calendar.MONDAY -> dateDay = "Monday"
            Calendar.TUESDAY -> dateDay = "Tuesday"
            Calendar.WEDNESDAY -> dateDay = "Wednesday"
            Calendar.THURSDAY -> dateDay = "Thursday"
            Calendar.FRIDAY -> dateDay = "Friday"
            Calendar.SATURDAY -> dateDay = "Saturday"
            Calendar.SUNDAY -> dateDay = "Sunday"
        }
        var monthName=""
        when(month){
            "1"->{
                monthName="Jan,"
            }
            "2"->{
                monthName="Feb,"
            }
            "3"->{
                monthName="Mar,"
            }
            "4"->{
                monthName="Apr,"
            }
            "5"->{
                monthName="May,"
            }
            "6"->{
                monthName="Jun,"
            }
            "7"->{
                monthName="Jul,"
            }
            "8"->{
                monthName="Aug,"
            }
            "9"->{
                monthName="Sep,"
            }
            "10"->{
                monthName="Oct,"
            }
            "11"->{
                monthName="Nov,"
            }
            "12"->{
                monthName="Dec,"
            }
        }

        holder.view.date.text = gridvalue
        holder.view.dateDay.text = dateDay
        holder.view.monthName.text = monthName

        holder.view.more.setOnClickListener {
            if (holder.view.ll_taskView.visibility == View.GONE) {
                holder.view.ll_taskView.visibility = View.VISIBLE
                holder.view.more.animate().rotation(180F).start()
            } else {
                holder.view.checkOne.isChecked = false
                holder.view.checkTwo.isChecked = false
                holder.view.ll_taskView.visibility = View.GONE
                holder.view.more.animate().rotation(0F).start();
            }
        }
    }


    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)


}


