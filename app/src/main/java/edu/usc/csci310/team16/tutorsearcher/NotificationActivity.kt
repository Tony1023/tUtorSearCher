package edu.usc.csci310.team16.tutorsearcher

import android.content.Context
import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationFragmentBinding

class NotificationActivity : Fragment() {

    lateinit var notificationModel: NotificationModel
    lateinit var layoutManager: RecyclerView.LayoutManager
    open lateinit var binding: NotificationFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationModel = NotificationModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.notification_fragment, container, false)
        binding.bind = notificationModel

        layoutManager = LinearLayoutManager(activity)


        return view
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private fun initDataset() {
        dataset = Array(DATASET_COUNT, {i -> "This is element # $i"})
    }

    companion object {
        private val TAG = "RecyclerViewFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
        private val DATASET_COUNT = 60
    }

    class NotificationListAdapter internal constructor(
        context: Context
    ) : RecyclerView.Adapter<NotifcationListAdapter.NotificationViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var notifications = emptyList<Notifications>() // Cached copy of words

        inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val notificationItemView: MaterialCardView = itemView.findViewById(R.id.notification_card)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
            val itemView = inflater.inflate(R.layout.notification_fragment, parent, false)
            return NotifciationViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
            val current = notifications[position]
        }

        internal fun setWords(words: List<Notifications>) {
            this.notifications = words
            notifyDataSetChanged()
        }

        override fun getItemCount() = notifications.size
    }
}