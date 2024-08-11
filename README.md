# TeruAC

TeruAntiCheat is Packet Based AntiCheat for 1.8.x.  

This wont need Protocolib or PacketEvents dependencies because it use NMS!

This plugin allows you to block cheater, but this still have a lot of issues need to be fixed. 

# I'll explain how checks are work.

## Timer[A] Check

This timer check is look to delay between two flying packets.

Basically this delay is exactly 50ms, because Flying packet will sent 20 times per seconds (1sec=1000ms) in 1.8.x.

I sample this delay and get average, if there are too much difference from 50ms, I'll flag!

It is checking delay's legitimately which means, allows detect Slow Timer Hacks.

This is very basic check.

## Invalid[A] Check, this is spoofed ground check.

First of all, there are method player.isOnGround() this is called client side ground. 

and It is trusting what does client say so it is easily to be spoofed. 

so This check prevent from this.

This check also detect Fly hacks without flying sometimes, 

cause for some blatant fly modes they will set client's ground as false, even on actually standing on ground.

So It can detect it.

# There are many other checks and they are have to be improved!

contributing are welcome.

Thanks you
