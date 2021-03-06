package ja.kobespiral.toDo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ja.kobespiral.toDo.dto.ToDoDto;
import ja.kobespiral.toDo.entity.ToDo;
import ja.kobespiral.toDo.exception.UserCheckException;
import ja.kobespiral.toDo.repository.ToDoRepository;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository todos;

    // todoを1つ追加する
    public ToDoDto addToDo(ToDo todo) {
        return ToDoDto.build(todos.save(todo));
    }

    // uidからtodoを取得する
    public List<ToDoDto> getToDo(String uid) {
        ArrayList<ToDoDto> list = new ArrayList<ToDoDto>();
        for (ToDo d : todos.findTodoByUid(uid)) {
            list.add(ToDoDto.build(d));
        }
        return list;
    }

    // 全てのtodoを取得する
    public List<ToDoDto> getAllToDo() {
        ArrayList<ToDoDto> list = new ArrayList<ToDoDto>();
        for (ToDo d : todos.findAll()) {
            list.add(ToDoDto.build(d));
        }
        return list;
    }

    // tidに対応するtodoの。isopenを変更する
    public String updateToDo(Long tid) {
        ToDo t = todos.findById(tid).orElseThrow(() -> new UserCheckException(404, "muri"));
        t.setOpen(!t.isOpen());
        todos.save(t);
        return "ok";
    }

}