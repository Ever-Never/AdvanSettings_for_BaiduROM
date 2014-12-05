#!/system/bin/sh

dir=/data/data/cn.edu.ncwu.www/files/recovery
busybox=$dir/busybox
res=$dir/res
recovery=$dir/recovery
script=$dir/install-recovery.sh
fstab=$dir/recovery.fstab

mount -o rw,remount /system

chmod 755 $busybox

$busybox cp -f $busybox /system/xbin
chmod 6755 /system/xbin/busybox

$busybox cp -f $recovery /system/xbin
chmod 755 /system/xbin/recovery

$busybox cp -f $script /system/etc
chmod 755 /system/etc/install-recovery.sh

$busybox cp -f $fstab /system/etc
chmod 644 /system/etc/recovery.fstab

$busybox cp -rf $res /system/media
chmod -R 644 /system/media/res

mount -o ro,remount /system

