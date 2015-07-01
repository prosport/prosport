package models;

import com.avaje.ebean.annotation.EnumMapping;

/**
 * Created by rumata on 4/18/15.
 */
@EnumMapping(nameValuePairs="ROLE_ADMIN=admin, ROLE_MANAGER=manager, ROLE_MODERATOR=moderator, ROLE_USER_BLOCKED=blocked, ROLE_NONE=n/a")
public enum SecurityRole {
    ROLE_ADMIN, ROLE_MANAGER, ROLE_MODERATOR, ROLE_USER_BLOCKED, ROLE_NONE;
}