Reproducer
===
for [https://youtrack.jetbrains.com/issue/IDEA-340177](https://youtrack.jetbrains.com/issue/IDEA-340177) 


1. Sync project
2. Run task: `notifications` > `build` > `buildDockerImage`
   This task is defined in `notifications/build.gradle.kts` and is simply building docker image via docker-compose
3. Run docker-compose services by clicking green arrow near "services" in `docker/docker.compose.yml`
4. Go to `Services` panel > `Docker-compose: reproducer` > `notifications` > `reproducer-notifications-*` image. Right click and copy its image SHA id
5. Change `Main.kt` file in `notifications` in any way.
6. Repeat step 2.
7. Stop `notifications` container in `Services` panel. Then at same `Services` panel right click and run `Start container`.
8. Copy image SHA id. You'll see same ID while we have already rebuilt image at step 6.
9. Stop the image and start it again by going to "docker-compose.yml" file and pressing greeen arrow next to `notifications` service.
10. After it starts copy image id in "Services" panel. You'll see that now it picked up a new image!

Expected behaviour would be to start container with latest built image from "Services" panel similar as if you start it from "docker-compose.yml" file.
Alternatively, if current behaviour is considered "the right way", there should be at least an action in context menu of selected service in "Services panel" to update configuration with latest available image