#!/system/bin/sh

dir=/data/data/cn.edu.ncwu.www/files/recovery


recovery=$dir/recovery/recovery.img


dd if=$recovery of=/dev/block/xxxx

