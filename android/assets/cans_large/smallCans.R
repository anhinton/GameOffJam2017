#!/bin/env Rscript
### smallCans.R
###
### This script resizes the full sized soda can files `<colour>_soda.png`
### and resizes them to the value stored as `playerSize`

playerSize = 80

images = list.files(pattern = "soda[.]png$")
output = file.path("..", "graphics",
                   gsub(pattern = "[.]png$", replacement = "_small.png",
                        x = images))

for (i in seq_along(images)) {
    system2(command = "magick.exe",
        args = c(images[i], "-resize", playerSize, output[i]))
}
