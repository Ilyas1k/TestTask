package com.example.testtaskapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var scheduleContainer: LinearLayout
    private lateinit var dayTextViews: List<TextView>
    private var selectedDay = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupDayClickListeners()
        updateScheduleForDay(selectedDay)
    }

    data class Lesson(
        val time: String,
        val name: String,
        val period: String,
    )
    private val scheduleData = mapOf(
        6 to listOf(
            Lesson("9:00 - 10:30", "Проектная деятельность (Практика)", "1 Сен - 2 Ноя"),
        ),
        7 to listOf(
            Lesson("9:00 - 10:30", "Подтверждение соответствия продукции и услуг (Практика)", "1 Сен - 1 Ноя"),
            Lesson("10:40 - 12:10", "Объектно-ориентированное программирование (Практика)", "1 Сен - 1 Ноя"),
            Lesson("12:20 - 13:50", "Объектно-ориентированное программирование (Лаб.работа)", "1 Сен - 1 Ноя"),
            Lesson("14:30 - 16:00", "Основы алгоритмизации и программирования (Практика)", "1 Сен - 1 Ноя")
        ),
        8 to listOf(
            Lesson("9:00 - 10:30", "Основы алгоритмизации и программирования (Лекция)", "1 Сен - 1 Ноя"),
            Lesson("10:40 - 12:10", "Основы алгоритмизации и программирования (Лекция)", "1 Сен - 1 Ноя"),
            Lesson("12:20 - 13:50", "Основы алгоритмизации и программирования (Лекция)", "1 Сен - 1 Ноя")
        ),
        9 to listOf(
            Lesson("9:00 - 10:30", "Общая физическая подготовка (Практика)", "1 Сен - 1 Ноя"),
        ),
        10 to listOf(
            Lesson("9:00 - 10:30", "Методы анализа и обработки сигналов (Лекция)", "1 Сен - 1 Ноя"),
            Lesson("10:40 - 12:10", "Квалиметрия и управление качестом (Лекция)", "1 Сен - 1 Ноя"),
        ),
        11 to listOf(
        )
    )

    private fun initViews() {
        scheduleContainer = findViewById(R.id.scheduleContainer)

        dayTextViews = listOf(
            findViewById(R.id.day6),
            findViewById(R.id.day7),
            findViewById(R.id.day8),
            findViewById(R.id.day9),
            findViewById(R.id.day10),
            findViewById(R.id.day11)
        )
    }

    private fun setupDayClickListeners() {
        dayTextViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                val clickedDay = index + 6
                selectDay(clickedDay)
                updateScheduleForDay(clickedDay)
            }
        }
    }

    private fun selectDay(day: Int) {
        dayTextViews.forEach { dayView ->
            dayView.setBackgroundResource(R.drawable.unselected_day_background)
            dayView.setTextColor(Color.BLACK)
            dayView.typeface = android.graphics.Typeface.DEFAULT
        }

        val selectedIndex = day - 6
        if (selectedIndex in dayTextViews.indices) {
            dayTextViews[selectedIndex].setBackgroundResource(R.drawable.selected_day_background)
            dayTextViews[selectedIndex].setTextColor(Color.WHITE)
            dayTextViews[selectedIndex].setTypeface(null, android.graphics.Typeface.BOLD)
        }

        selectedDay = day
    }

    private fun updateScheduleForDay(day: Int) {
        scheduleContainer.removeAllViews()

        val daySchedule = scheduleData[day] ?: emptyList()

        if (daySchedule.isEmpty()) {
            val noLessonsView = TextView(this).apply {
                text = "Занятий нет"
                textSize = 16f
                setTextColor(Color.GRAY)
                setPadding(0, 32.dpToPx(), 0, 0)
                gravity = android.view.Gravity.CENTER
            }
            scheduleContainer.addView(noLessonsView)
        } else {
            daySchedule.forEach { lesson ->
                val lessonView = createLessonView(lesson)
                scheduleContainer.addView(lessonView)

                if (lesson != daySchedule.last()) {
                    val divider = View(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1
                        ).apply {
                            setMargins(0, 16.dpToPx(), 0, 16.dpToPx())
                        }
                        setBackgroundColor(Color.parseColor("#E0E0E0"))
                    }
                    scheduleContainer.addView(divider)
                }
            }
        }
    }

    private fun createLessonView(lesson: Lesson): View {
        return LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setBackgroundResource(R.drawable.lesson_card_background)
            setPadding(16.dpToPx(), 16.dpToPx(), 16.dpToPx(), 16.dpToPx())

            addView(TextView(this@MainActivity).apply {
                text = lesson.time
                textSize = 16f
                setTextColor(Color.BLACK)
                setTypeface(null, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 8.dpToPx()
                }
            })

            addView(TextView(this@MainActivity).apply {
                text = lesson.name
                textSize = 14f
                setTextColor(Color.BLACK)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 8.dpToPx()
                }
            })

            addView(TextView(this@MainActivity).apply {
                text = lesson.period
                textSize = 12f
                setTextColor(Color.parseColor("#666666"))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            })
        }
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

}
