package com.adrpien.scribbles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

    // The Paint class holds the style and color information about how to draw geometries, text and bitmaps.
    val paint = Paint()
    val circles: ArrayList<Circle> = ArrayList()
    private lateinit var circle :Circle


    // onTouchEvent implementation
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event != null){
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    // Saving touch down coordinates and setting them as circle center
                    val pointF = PointF(event.x,event.y)
                    circle = Circle()
                    circle.originalPointF = pointF
                }
                MotionEvent.ACTION_UP -> {
                    // Saving touch up coordinates and setting them as circle border
                    val pointF = PointF(event.x,event.y)
                    circle.destinationPointF = pointF
                    circles.add(circle)
                    // Forcing to redraw View
                    invalidate()
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.RED

        // Drawing circles in view
        for(circle in circles) {
            // radius calculation
            val radius = Math.sqrt(
                Math.pow((circle.originalPointF.x - circle.destinationPointF.x).toDouble(), 2.0) -
                        Math.pow((circle.originalPointF.y - circle.destinationPointF.y).toDouble(), 2.0)
            )

            // A Canvas is a 2D drawing framework that provides us with methods to draw on the underlying Bitmap.
            // A Bitmap acts as the surface over which the canvas is placed.
            // The Paint class is used to provide colors and styles
            canvas?.drawCircle(
                circle.originalPointF.x,
                circle.originalPointF.y,
                radius.toFloat(),
                paint
            )
        }

        /*
        * https://www.journaldev.com/25182/android-canvas
        *
        * The Path class encapsulates compound (multiple contour) geometric paths consisting of
        * straight line segments, quadratic curves, and cubic curves.
        * To create a Path, two methods are important: moveTo() and lineTo().
        * moveTo takes you to the coordinates you specify on the screen.
        * lineTo draws a line from the current position to the one specified.
        * close() is used to close a path.
        */
    }
}