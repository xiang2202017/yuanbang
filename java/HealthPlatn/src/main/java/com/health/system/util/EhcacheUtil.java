package com.health.system.util;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil
{
  @SuppressWarnings("unused")
private static final String path = "/ehcache.xml";
  private URL url;
  private CacheManager manager;
  private static EhcacheUtil ehCache;
  
  private EhcacheUtil(String path)
  {
    this.url = getClass().getResource(path);
    this.manager = CacheManager.create(this.url);
  }
  
  public static EhcacheUtil getInstance()
  {
    if (ehCache == null) {
      ehCache = new EhcacheUtil("/ehcache.xml");
    }
    return ehCache;
  }
  
  public void put(String cacheName, String key, Object value)
  {
    Cache cache = this.manager.getCache(cacheName);
    Element element = new Element(key, value);
    cache.put(element);
  }
  
  public Object get(String cacheName, String key)
  {
    Cache cache = this.manager.getCache(cacheName);
    Element element = cache.get(key);
    return element == null ? null : element.getObjectValue();
  }
  
  public Cache get(String cacheName)
  {
    return this.manager.getCache(cacheName);
  }
  
  public void remove(String cacheName, String key)
  {
    Cache cache = this.manager.getCache(cacheName);
    cache.remove(key);
  }
}
