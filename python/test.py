import cv2
from dip import contrast_stretch

filepath = "../Images/DIP3E_Original_Images_CH03/"
filename = filepath + "Fig0320(1)(top_left).tif"
outpath = "../Images/Output/"

# Reading Images 
image = cv2.imread(filename, cv2.IMREAD_GRAYSCALE)

# mat = [0] * 256

# for row in image:
#     for col in row:
#         mat[col] += 1

# print(mat)

# outputImage = image.copy()

outputImage = contrast_stretch(image)
cv2.imwrite(outpath + "Fig0320Edited.jpg", outputImage)