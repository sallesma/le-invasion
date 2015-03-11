Set oArgs=WScript.Arguments
		 
if oArgs.count > 0 then
	set WshShell = WScript.CreateObject("WScript.Shell")
		WshShell.AppActivate oArgs(0)
		if (WshShell.AppActivate (oArgs(0)) = true) then
			if oArgs.count > 1 then
				WScript.Sleep 100
				WshShell.SendKeys oArgs(1)
				WshShell.SendKeys "~"
			end if
		end if

	if oArgs.count > 2 then
		WScript.Sleep 100
		WshShell.AppActivate oArgs(2)
	end if
end if