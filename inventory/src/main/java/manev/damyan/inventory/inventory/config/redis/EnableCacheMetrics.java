package manev.damyan.inventory.inventory.config.redis;

import manev.damyan.inventory.inventory.items.ItemsService;
import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class EnableCacheMetrics {

    private final CacheMetricsRegistrar cacheMetricsRegistrar;
    private final CacheManager cacheManager;

    public EnableCacheMetrics(CacheMetricsRegistrar cacheMetricsRegistrar, CacheManager cacheManager) {
        this.cacheMetricsRegistrar = cacheMetricsRegistrar;
        this.cacheManager = cacheManager;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void addCachesMetrics() {
        cacheMetricsRegistrar.bindCacheToRegistry(cacheManager.getCache(ItemsService.ITEMS_CACHE));

        cacheMetricsRegistrar.bindCacheToRegistry(cacheManager.getCache("testMetrics"));
    }
}