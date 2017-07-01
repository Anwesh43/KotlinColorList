package com.anwesome.games.kotlincolorlist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

/**
 * Created by anweshmishra on 01/07/17.
 */
class CustomView(ctx:Context):View(ctx) {
    var colors:ArrayList<Int> = ArrayList<Int>()
    var paint:Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var w:Int = 0
    var h:Int = 0
    var time:Int = 0
    var hSize:Int = 0
    var colorRects:ArrayList<ColorRect> = ArrayList()
    var animationHandler:AnimationHandler?=null
    fun addColor(color:Int) {
        this.colors.add(color)
    }
    override fun onDraw(canvas:Canvas) {
        if(time == 0){
            w = canvas.width
            h = canvas.height
            if(colors.size > 0) {
                var hSize:Int = h/(colors.size)
                var i:Int = 0
                colors.forEach {
                    colorRects.add(ColorRect(i,it,hSize))
                    i++
                }
                hSize = h/colors.size
            }
            animationHandler = AnimationHandler(this)
        }
        colorRects.forEach {
            it.draw(canvas,paint,w)
        }
        time++
        animationHandler?.animate()
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            val y = event.y
            colorRects.forEach {
                if(it.handleTap(y)) {
                    animationHandler?.startAnimating(it)
                }
            }
        }
        return true
    }
}
class ColorRect {
    var index:Int = 0
    var scale:Float = 0.0f
    var color:Int = 0
    var y:Float = 0.0f
    var dir:Float = 0.0f
    var hSize:Int = 0
    constructor(index:Int,color:Int,hSize:Int) {
        this.index = index
        this.color = color
        this.y = hSize*index*1.0f
        this.hSize = hSize
    }
    fun draw(canvas:Canvas,paint:Paint,w:Int) {
        val x = (w/2)*1.0f
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = (hSize/30.0f)
        canvas.save()
        canvas.translate(x,y+hSize/2)
        canvas.drawRect(Rect(-w/2,-hSize/2,w/2,hSize/2),paint)
        paint.style = Paint.Style.FILL
        canvas.save()
        canvas.scale(scale,1.0f)
        canvas.drawRect(Rect(-w/2,-hSize/2,w/2,hSize/2),paint)
        canvas.restore()
        canvas.restore()
    }
    fun update() {
        scale += dir*0.2f
        if(scale > 1.0f) {
            scale = 1.0f
            dir = 0.0f
        }
        if(scale < 0) {
            scale = 0.0f
            dir = 0.0f
        }
    }
    fun startUpdating(dir:Float) {
        this.dir = dir
    }
    override fun hashCode():Int {
        return index
    }
    fun handleTap(y:Float):Boolean {
        return y>=this.y && y<=this.y+hSize
    }
    fun stopped():Boolean  {
        return dir == 0.0f
    }
}
class AnimationHandler {
    var prev:ColorRect? = null
    var curr:ColorRect? = null
    var animated:Boolean = false
    var v:View?=null
    constructor(v:View) {
        this.v = v
    }
    fun animate() {
        if(animated) {
            curr?.update()
            prev?.update()
            if(curr?.stopped() == true) {
                prev = curr
                animated = false
                curr = null
            }
            try {
                Thread.sleep(50)
                v?.invalidate()
            }
            catch (ex:Exception) {

            }
        }
    }
    fun startAnimating(colorRect:ColorRect) {
        if(!animated) {
            curr = colorRect
            curr?.startUpdating(1.0f)
            prev?.startUpdating(-1.0f)
            v?.postInvalidate()
            animated = true
        }
    }
}