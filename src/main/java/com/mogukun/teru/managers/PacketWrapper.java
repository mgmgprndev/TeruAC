package com.mogukun.teru.managers;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.WeakHashMap;

public class PacketWrapper {

    private final Map<String, Field> fields = new WeakHashMap<>();

    private final Packet<?> instance;
    private final Class<? extends Packet<?>> clazz;

    public PacketWrapper(final Packet<?> instance, final Class<? extends Packet<?>> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();

        for(Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();

            declaredField.setAccessible(true);
            fields.put(fieldName, declaredField);
        }

        this.instance = instance;
        this.clazz = clazz;
    }

    public Entity getTarget(final World world) {
        if(get("a") == null) {
            return null;
        }

        final int entityId = get("a");

        net.minecraft.server.v1_8_R3.Entity entity = world.a(entityId);

        if(entity == null) {
            return null;
        }

        return entity.getBukkitEntity();
    }

    public <T> T get(String name) {
        try {
            return (T) fields.get(name).get(instance);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
