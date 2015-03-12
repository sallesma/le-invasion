#! /bin/bash

wmctrl -a "$1";
xdotool type "$2";
xdotool key "Return";