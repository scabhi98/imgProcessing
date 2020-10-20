import numpy as np
from enum import Enum

def negative(inImage, bpp=8):
    upper = 2 ** bpp - 1
    out = np.zeros(inImage.shape)
    (r,c) = inImage.shape
    for row in range(r-1):
        for col in range(c-1):
            out[row,col] = upper - inImage[row,col]
    return out

def contrast_stretch(inImage, bpp=8):
    depth = 2 ** bpp
    (_min, _max) = (depth, 0)
    out = np.zeros(inImage.shape)
    (rows,cols) = inImage.shape
    (rows,cols) = (rows - 1, cols - 1)
    for row in inImage:
        if _min > min(row):
            _min = min(row)
        if _max < max(row):
            _max = max(row)
    r = 0
    while r < rows:
        c = 0
        while c < cols:
            out[r, c] = ( (inImage[r, c] - _min) / (_max - _min) ) * depth
            c = c + 1
        r = r + 1
    return out
