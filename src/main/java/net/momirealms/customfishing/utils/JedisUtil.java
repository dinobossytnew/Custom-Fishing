/*
 *  Copyright (C) <2022> <XiaoMoMi>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.momirealms.customfishing.utils;

import net.momirealms.customfishing.helper.Log;
import org.bukkit.configuration.file.YamlConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

public class JedisUtil {

    private static JedisPool jedisPool;
    public static boolean useRedis;

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void initializeRedis(YamlConfiguration configuration){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setNumTestsPerEvictionRun(-1);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(configuration.getInt("redis.MinEvictableIdleTimeMillis",1800000));
        jedisPoolConfig.setMaxTotal(configuration.getInt("redis.MaxTotal",8));
        jedisPoolConfig.setMaxIdle(configuration.getInt("redis.MaxIdle",8));
        jedisPoolConfig.setMinIdle(configuration.getInt("redis.MinIdle",1));
        jedisPoolConfig.setMaxWaitMillis(configuration.getInt("redis.MaxWaitMillis",30000));

        jedisPool = new JedisPool(jedisPoolConfig, configuration.getString("redis.host","localhost"), configuration.getInt("redis.port",6379));

        AdventureManager.consoleMessage("<gradient:#0070B3:#A0EACF>[CustomFishing] <color:#E1FFFF>Redis Enabled!");

        List<Jedis> minIdleJedisList = new ArrayList<>(jedisPoolConfig.getMinIdle());
        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis;
            try {
                jedis = jedisPool.getResource();
                minIdleJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                Log.warn(e.getMessage());
            }
        }

        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis;
            try {
                jedis = minIdleJedisList.get(i);
                jedis.close();
            } catch (Exception e) {
                Log.warn(e.getMessage());
            }
        }
    }
}
