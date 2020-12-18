package dev.jacobandersen.jorstad.ext

val roleRegex = Regex("<@&(\\d+)>")
val userRegex = Regex("<@!(\\d+)>")

fun String.matchesRoleRegex() = roleRegex.matches(this)

fun String.matchesUserRegex() = userRegex.matches(this)

fun String.getIdFromRoleMention(): Long? {
    return roleRegex.matchEntire(this)?.groupValues?.get(1)?.toLongOrNull()
}

fun String.getIdFromUserMention(): Long? {
    return userRegex.matchEntire(this)?.groupValues?.get(1)?.toLongOrNull()
}