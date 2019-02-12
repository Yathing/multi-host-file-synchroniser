<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Task;
use Illuminate\Support\Facades\Auth;



class ToDoController extends Controller
{
    public function index()
    {
        $tasks = Task :: all();
        return view('index',compact('tasks'));
    }

    public function store(Request $request) //method for saving new task
    {
        if($request->input('task'))
        {
            $task = new Task;  //if we want to save new task, we must create new $task object
            $task->content = $request->input('task'); //save the value to the form which filed named task to the content column
            Auth::user()->tasks()->save($task);
        }
        return redirect()->back();
    }

    public function edit($id)
    {
        $task = Task::find($id);
        return view('edit',['task'=>$task]);
    }


    public function update($id, Request $request)
    {
        if($request->input('task'))
        {
            $task = Task::find($id);
            $task->content = $request->input('task');
            $task->save();
        }
        return redirect('/');// redirect to the main page 
    }

    public function delete($id)
    {
        $task = Task::find($id);
        $task->delete();
        return redirect()->back();
    }

    //download files from local
    // public function file_download()
    // {
    //     $file = 'https://img.freepik.com/free-vector/abstract-dynamic-pattern-wallpaper-vector_53876-59131.jpg';
    //     $name = basename($file);
    //     return response()->download($file, $name);
    // }

        //190210 download files from url
    public function file_download()
    {
        $url = "https://img.freepik.com/free-vector/abstract-dynamic-pattern-wallpaper-vector_53876-59131.jpg";
        //$url = "http://www.cs.ucsb.edu/~arlei/pubs/sdm18.pdf";
        $path_parts = pathinfo($url);
        $file = file_get_contents($url);
        $extension = pathinfo($url, PATHINFO_EXTENSION);
        $name = $path_parts['filename'];
        $filename = date("Y.m.d.H-i-s").'.'.$name.'.'.$extension;
        
        echo $filename;
        $save = file_put_contents($filename, $file);
            var_dump('file downloaded to /public');
    }
}
