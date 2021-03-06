package com.example.hangman.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.R
import com.example.hangman.data.model.Room
import com.example.hangman.presenter.MainPresenter
import com.example.hangman.ui.activity.RoomActivity

class MainAdapter(private var roomList: ArrayList<Room>, val presenter : MainPresenter) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = roomList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(roomList[position])
    }

    fun setList(roomList : ArrayList<Room>) {
        this.roomList = roomList
        notifyDataSetChanged()
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvRoomName: TextView = itemView.findViewById(R.id.tv_room_name_main_item)
        private val tvCount: TextView = itemView.findViewById(R.id.tv_count_main_item)
        private val itemLayout : ConstraintLayout = itemView.findViewById(R.id.layout_item_main)

        fun bind(room : Room) {
            tvRoomName.text = room.name
            tvCount.text = "${room.participants?.size}/${room.maxPlayer}"
            itemLayout.setOnClickListener {
                room.id?.let { it1 -> presenter.joinRoom(it1) }

                val intent = Intent(itemView.context, RoomActivity::class.java)
                intent.putExtra("roomId", room.id)
                // 추후 MVP로 제대로된 리팩토링 할때 room id 가져올 때 presenter에서 가져와야 하는가? 아니면 여기서 해도 되는가 등에 대해서 논의한다.
                itemView.context.startActivity(intent)
            }
        }
    }
}