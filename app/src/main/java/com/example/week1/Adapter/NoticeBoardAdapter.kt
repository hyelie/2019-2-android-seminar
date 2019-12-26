package com.example.week1.Adapter
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.week1.Model.NoticeBoardItem
import com.example.week1.R
import kotlinx.android.synthetic.main.noticeboard_item.view.*
import org.w3c.dom.Text
import java.lang.ref.WeakReference
import kotlinx.android.synthetic.main.noticeboard_item.*



class NoticeBoardAdapter<T>(private var clickHandler: T) : RecyclerView.Adapter<NoticeBoardAdapter<T>.ViewHolder>(){
    private lateinit var context: Context
    private var postdata: ArrayList<NoticeBoardItem>?=null

    interface clickListener{
        fun go_nextpage(data: NoticeBoardItem)
    }

    inner class ViewHolder(v : View, listener:clickListener): RecyclerView.ViewHolder(v), View.OnClickListener{
        val listenerRef : WeakReference<clickListener> = WeakReference(listener)

        //표기할 변수들
        var each_card : CardView = v.findViewById(R.id.each_post)
        var dataTime : TextView = v.findViewById(R.id.NoticeBoardItem_time)
        var dataWriter : TextView = v.findViewById(R.id.NoticeBoardItem_writer)
        var dataTitle : TextView = v.findViewById(R.id.NoticeBoardItem_title)

        //viewholder를 시작할 때 적용할 함수들. 여기서는 각 view들이 클릭되었을 때에 대한 listener를 지정
        init{
            each_card.setOnClickListener(this)
        }

        //클릭되었을 때는 go_nextpage를 사용함.
        override fun onClick(v: View?) {
            listenerRef.get()?.go_nextpage(postdata!![adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thisitem = postdata!![position]

        holder.dataTime.text = thisitem.time
        holder.dataTitle.text = thisitem.title
        holder.dataWriter.text = thisitem.writer
    }

    override fun getItemCount(): Int {
        if(postdata == null) return 0
        else return postdata!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =  LayoutInflater.from(context).inflate(R.layout.noticeboard_item, parent, false)
        return ViewHolder(view, clickHandler as clickListener)
    }

    //refresh를 하기 위함 함수. data가 바뀌었다는 것을 알려줌
    fun setData(data: ArrayList<NoticeBoardItem>){
        postdata = data
        notifyDataSetChanged()
    }
}