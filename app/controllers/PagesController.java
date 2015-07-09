package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.StaticPage;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by andy on 06.07.15.
 */
public class PagesController extends Controller {
    //TODO: если потребуется, можно легко вынести общий контроллер для любой сущности на темплейтах (абстрактный класc<T> подойдет)

    public static Result getPages() {
        List<StaticPage> allPages = StaticPage.find.all();
        JsonNode result = Json.toJson(allPages);
        return ok(result);
    }

    public static Result getPage(long pageId) {
        StaticPage page = StaticPage.find.byId(pageId);
        if(page == null) return badRequest("no page with id = " + pageId);
        return ok(Json.toJson(page));
    }


    public static Result createPage() {
        JsonNode json = request().body().asJson();
        StaticPage person = Json.fromJson(json, StaticPage.class);

        person.save();
        return ok();
    }

    public static Result updatePage() {
        JsonNode json = request().body().asJson();
        StaticPage fromRequest = Json.fromJson(json, StaticPage.class);
        StaticPage entity = StaticPage.find.byId(fromRequest.id);
        if(entity == null)
            return badRequest("no page with id = " + fromRequest.id);

        entity.name = fromRequest.name;
        entity.url = fromRequest.url;
        entity.content = fromRequest.content;
        entity.save();

        return ok();
    }

    public static Result deletePage() {
        JsonNode json = request().body().asJson();
        StaticPage fromRequest = Json.fromJson(json, StaticPage.class);
        StaticPage entity = StaticPage.find.byId(fromRequest.id);
        if(entity == null)
            return badRequest("no page with id = " + fromRequest.id);
        entity.delete();

        return ok();
    }


}
