package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students, ::onEditStudent, ::onDeleteStudent)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showAddStudentDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_edit_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        // Handle adding student
        val name = dialogView.findViewById<EditText>(R.id.edit_student_name).text.toString()
        val id = dialogView.findViewById<EditText>(R.id.edit_student_id).text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          students.add(StudentModel(name, id))
          studentAdapter.notifyDataSetChanged()
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun onEditStudent(student: StudentModel, position: Int) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_edit_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Update") { _, _ ->
        val name = dialogView.findViewById<EditText>(R.id.edit_student_name).text.toString()
        val id = dialogView.findViewById<EditText>(R.id.edit_student_id).text.toString()
        if (name.isNotEmpty() && id.isNotEmpty()) {
          students[position] = StudentModel(name, id)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialogView.findViewById<EditText>(R.id.edit_student_name).setText(student.studentName)
    dialogView.findViewById<EditText>(R.id.edit_student_id).setText(student.studentId)
    dialog.show()
  }

  private fun onDeleteStudent(student: StudentModel, position: Int) {
    val dialog = AlertDialog.Builder(this)
      .setTitle("Delete Student")
      .setMessage("Are you sure you want to delete ${student.studentName}?")
      .setPositiveButton("Delete") { _, _ ->
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)
        Snackbar.make(findViewById(R.id.recycler_view_students), "${student.studentName} deleted", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(position, student)
            studentAdapter.notifyItemInserted(position)
          }.show()
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }
}
