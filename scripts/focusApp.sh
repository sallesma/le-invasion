#! /bin/bash

current=`xdotool getwindowfocus`;
wmctrl -a "$1";
xdotool type "$2";
xdotool key "Return";
xdotool windowfocus "$current";
