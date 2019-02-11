@extends('layouts.master')
@section('content')
<table>
            <thead>
                <tr>
                    <th>File</th>
                    @isAdmin
                    <th>Assigned to</th>
                    @endisAdmin
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>

            <tbody>
                 @foreach($tasks as $task)
                <tr>
                    <td><a href="">
                        @if(!$task->status)
                        {{ $task->content }}
                        @else
                        <strike class="grey-text">{{ $task->content }}</strike>
                        @endif
                    </a></td>
            @isAdmin<td>{{ $task->user->name }}</td>@endisAdmin
                    <td><a title="edit" href="{{ route('edit',$task->id) }}"><i class="small material-icons">edit</i></a></td>
                    <td><a title="delete" onclick="return confirm('Delete?');" href="{{ route('delete',$task->id) }}"><i class="small material-icons">delete_forever</i></a></td>
                </tr> 
                @endforeach
            </tbody>
        </table>

        <ul class="pagination">
            <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
            <li class="active"><a href="#!">1</a></li>
            <li class="waves-effect"><a href="#!">2</a></li>
            <li class="waves-effect"><a href="#!">3</a></li>
            <li class="waves-effect"><a href="#!">4</a></li>
            <li class="waves-effect"><a href="#!">5</a></li>
            <li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
        </ul>

    <form method="POST" action="{{ route('store') }}" class="col s12">
            <div class="row">
                <div class="input-field col s12">
                    <input name="task" id="text" type="text" class="validate">
                    <label for="text">New File</label>
                </div>
            </div>

            @include('partials.coworkers')

            <button type="submit" class="waves-effect waves-light btn">Add new File</button>
            @csrf
        </form>

        @isWorker
        <br><br>
        <form action="" class="col s12">
          <div class="input-field">
            <select>
                <option value="" disabled selected>Send invitation to:</option>
                <option value="1">zihao</option>
                <option value="2">Geo 2</option>
                <option value="3">Tom</option>
                <option value="4">Yixin</option>
                <option value="5">myself</option>
            </select>
            <label>Send invitation</label>
         </div>  
         <a class="waves-effect waves-light btn">Send invitation</a>
        </form>
        
        @endisWorker

        @isAdmin
        <br><br>
        <ul class="collection with-header">
            <li class="collection-header">
                <h4>My Co-workers</h4>
            </li>
            <li class="collection-item">
                <div>zihao<a href="#!" class="secondary-content">delete</a></div>
            </li>
            <li class="collection-item">
                <div>Geo<a href="#!" class="secondary-content">delete</a></div>
            </li>
            <li class="collection-item">
                <div>Tom<a href="#!" class="secondary-content">delete</a></div>
            </li>
            <li class="collection-item">
                <div>Yixin<a href="#!" class="secondary-content">delete</a></div>
            </li>
        </ul>
        @endisAdmin

 
@endsection
