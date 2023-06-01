package com.qnocks.billy.core.aop;

import com.qnocks.billy.tenant.entity.Tenant;

public class TenantContext {

    private static final ThreadLocal<Tenant> currentTenant = new InheritableThreadLocal<>();
    private static final ThreadLocal<String> currentTenantId = new InheritableThreadLocal<>();

    public static Tenant getCurrentTenant() {
        return currentTenant.get();
    }

    public static String getCurrentTenantId() {
        return currentTenantId.get();
    }

    public static void setCurrentTenant(Tenant tenant) {
        currentTenant.set(tenant);
        currentTenantId.set(tenant.getId());
    }

    public static void clear() {
        currentTenant.set(null);
    }
}
