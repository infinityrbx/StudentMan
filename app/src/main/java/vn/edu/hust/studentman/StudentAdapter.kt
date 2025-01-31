package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
  private val students: List<StudentModel>,
  private val onEdit: (StudentModel, Int) -> Unit,
  private val onDelete: (StudentModel, Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)

    fun bind(student: StudentModel) {
      textStudentName.text = student.studentName
      textStudentId.text = student.studentId
      imageEdit.setOnClickListener { onEdit(student, adapterPosition) }
      imageRemove.setOnClickListener { onDelete(student, adapterPosition) }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    holder.bind(students[position])
  }

  override fun getItemCount(): Int = students.size
}
