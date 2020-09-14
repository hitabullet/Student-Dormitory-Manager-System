package cn.edu.ncc.zsh.controller;


import cn.edu.ncc.zsh.pojo.Record;
import cn.edu.ncc.zsh.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("studentNum")
    public Record StudentNum(Record record){
        return recordService.findSNum(record);
    }

    @GetMapping("people")
    public Record People(Record record){
        return recordService.find(record);
    }

    @GetMapping("quit")
    public Record Quit(Record record){
        return recordService.findQuit(record);
    }

    @GetMapping("maintian")
    public Record maintain(Record record){
        return recordService.findMaintain(record);
    }

    @GetMapping("leave")
    public Record Leave(Record record){
        return recordService.findLeave(record);
    }

    @GetMapping("repair")
    public List<Record> Repair(Record record){
        return recordService.findRepair(record);
    }
}
