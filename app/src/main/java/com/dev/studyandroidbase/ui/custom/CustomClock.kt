package com.dev.studyandroidbase.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.dev.studyandroidbase.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("Recycle", "SimpleDateFormat")
class CustomClock: View {
	
	constructor(context: Context) : this(context, null)
	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
		context,
		attrs,
		defStyleAttr
	) {
		init(context, attrs)
	}
	
	var mColorStrock: Int? = null
	var mWidthStrock: Int? = null
	var mXTitle: Int? = null
	var mYTitle: Int? = null
	var mFormatDate: SimpleDateFormat? = null
	var mFormatTime: SimpleDateFormat? = null
	var mPaint = Paint()
	
	private fun init(context: Context, attrs: AttributeSet?) {
		val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomClock)
		mColorStrock = typedArray.getColor(R.styleable.CustomClock_color_strock, Color.GREEN)
		mWidthStrock = typedArray.getDimensionPixelOffset(R.styleable.CustomClock_width_strock_circle, 20)
		mXTitle = typedArray.getDimensionPixelOffset(R.styleable.CustomClock_x_title, 160)
		mYTitle = typedArray.getDimensionPixelOffset(R.styleable.CustomClock_y_title, 160)
		typedArray.recycle()
		
		mFormatDate = SimpleDateFormat("dd/MM/yyyy")
		mFormatTime = SimpleDateFormat("hh:mm")
		
		mPaint.style = Paint.Style.STROKE
		mPaint.textSize = 150f
	}
	
	@SuppressLint("DrawAllocation")
	override fun onDraw(canvas: Canvas?) {
		super.onDraw(canvas)
		val rectOval = RectF((mWidthStrock!! / 2).toFloat(),
			(mWidthStrock!! / 2).toFloat(), (width - mWidthStrock!! / 2).toFloat(),
			(height - mWidthStrock!! / 2).toFloat())
		
		mPaint.strokeWidth = mWidthStrock!!.toFloat()
		mPaint.color = mColorStrock!!
		canvas!!.drawOval(rectOval, mPaint)
		
		// draw text
		mPaint.strokeWidth = 10f
		mPaint.color = Color.parseColor("#8BC34A")
		canvas.drawText("Smile", mXTitle!!.toFloat(), mYTitle!!.toFloat(), mPaint)
		
		// draw date
		val date = Date()
		val strDate = mFormatDate!!.format(date)
		mPaint.textSize = 100f
		canvas.drawText(strDate, 250f, 450f, mPaint)
		
		val strTime = mFormatTime!!.format(date)
		canvas.drawText(strTime, 250f, 600f, mPaint)
	}
}