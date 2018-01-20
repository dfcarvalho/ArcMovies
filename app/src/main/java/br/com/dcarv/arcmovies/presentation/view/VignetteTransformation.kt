package br.com.dcarv.arcmovies.presentation.view

import android.graphics.*
import android.opengl.ETC1.getHeight
import com.squareup.picasso.Transformation


/**
 * Created by dfcarvalho on 18/01/18.
 */
class VignetteTransformation(private val _type: TYPE) : Transformation {

    enum class TYPE {
        BOTTOM, TOP
    }

    override fun transform(source: Bitmap): Bitmap {

        // create the output bitmap structure
        val outputBitmap = Bitmap.createBitmap(source.width,
                source.height,
                source.config)

        // prepare canvas + paint with all ingredients
        val composeShader = _getVignetteShader(source)
        val canvas = Canvas(outputBitmap)
        val paint = Paint()
        paint.shader = composeShader
        paint.isAntiAlias = true

        // paint on canvas which holds to be rendered bitmap
        canvas.drawPaint(paint)

        // recycle the original bitmap which we no longer require
        source.recycle()

        return outputBitmap
    }

    override fun key(): String {
        return "co.kaush.mystarterapp.myappmodule.ui.transformations.VignetteTransformation"
    }

    private fun _getVignetteShader(source: Bitmap): ComposeShader {// create the bitmap shader
        // create a bitmap shader
        val bitmapShader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // create the linear gradient shader
        val linearGradientShader = _getLinearGradient(source)

        // create a shader that combines both effects
        return ComposeShader(bitmapShader, linearGradientShader, PorterDuff.Mode.DST_OUT)
    }

    private fun _getLinearGradient(source: Bitmap): LinearGradient {

        val x0 = 0
        val y0 = 0
        val x1 = 0
        val y1 = source.height
        // The colors here are actually irrelevant
        var fromColor = Color.RED
        // This translates to the image showing
        var toColor = Color.TRANSPARENT       // This translates to background color

        when (_type) {
            VignetteTransformation.TYPE.BOTTOM -> {
                fromColor = Color.TRANSPARENT
                toColor = Color.RED
            }
        }

        return LinearGradient(x0.toFloat(), y0.toFloat(), x1.toFloat(), y1.toFloat(), fromColor, toColor, Shader.TileMode.MIRROR)
    }
}