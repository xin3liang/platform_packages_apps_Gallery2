/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.gallery3d.filtershow.crop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public abstract class CropDrawingUtils {

    public static void drawRuleOfThird(Canvas canvas, RectF bounds) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.argb(128, 255, 255, 255));
        p.setStrokeWidth(2);
        float stepX = bounds.width() / 3.0f;
        float stepY = bounds.height() / 3.0f;
        float x = bounds.left + stepX;
        float y = bounds.top + stepY;
        for (int i = 0; i < 2; i++) {
            canvas.drawLine(x, bounds.top, x, bounds.bottom, p);
            x += stepX;
        }
        for (int j = 0; j < 2; j++) {
            canvas.drawLine(bounds.left, y, bounds.right, y, p);
            y += stepY;
        }
    }

    public static void drawCropRect(Canvas canvas, RectF bounds) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(3);
        canvas.drawRect(bounds, p);
    }

    public static void drawIndicator(Canvas canvas, Drawable indicator, int indicatorSize,
            float centerX, float centerY) {
        int left = (int) centerX - indicatorSize / 2;
        int top = (int) centerY - indicatorSize / 2;
        indicator.setBounds(left, top, left + indicatorSize, top + indicatorSize);
        indicator.draw(canvas);
    }

    public static void drawIndicators(Canvas canvas, Drawable cropIndicator, int indicatorSize,
            RectF bounds, boolean fixedAspect, int selection) {
        boolean notMoving = (selection == CropObject.MOVE_NONE);
        if (fixedAspect) {
            if ((selection == CropObject.TOP_LEFT) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.left, bounds.top);
            }
            if ((selection == CropObject.TOP_RIGHT) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.right, bounds.top);
            }
            if ((selection == CropObject.BOTTOM_LEFT) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.left, bounds.bottom);
            }
            if ((selection == CropObject.BOTTOM_RIGHT) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.right, bounds.bottom);
            }
        } else {
            if (((selection & CropObject.MOVE_TOP) != 0) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.centerX(), bounds.top);
            }
            if (((selection & CropObject.MOVE_BOTTOM) != 0) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.centerX(), bounds.bottom);
            }
            if (((selection & CropObject.MOVE_LEFT) != 0) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.left, bounds.centerY());
            }
            if (((selection & CropObject.MOVE_RIGHT) != 0) || notMoving) {
                drawIndicator(canvas, cropIndicator, indicatorSize, bounds.right, bounds.centerY());
            }
        }
    }

    public static Matrix getBitmapToDisplayMatrix(RectF imageBounds, RectF displayBounds) {
        Matrix m = new Matrix();
        CropDrawingUtils.setBitmapToDisplayMatrix(m, imageBounds, displayBounds);
        return m;
    }

    public static boolean setBitmapToDisplayMatrix(Matrix m, RectF imageBounds,
            RectF displayBounds) {
        m.reset();
        return m.setRectToRect(imageBounds, displayBounds, Matrix.ScaleToFit.CENTER);
    }

}
