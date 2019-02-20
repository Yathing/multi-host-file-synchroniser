@extends('layouts.nav')
@section('content')
   <br>
   <h2 style="display:inline;">Folders</h2>
   <a class="btn btn-info" style="float:right" href="{{ route('folders.create') }}"> Add new folders</a>
   <hr>


    <!-- <div style="padding:0px 100px 0px 100px;"> -->

    <div class="row">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <h3 class="box-title">All</h3>

                            <div class="table-responsive">
                                <table class="table ">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Time</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    @foreach ($folders as $folder)
                                        <tr>
                                            <td class="txt-oflo">{{ $folder->name}}</td>
                                            <td><span class="text-success">{{ $folder->created_at}}</span></td>
                                            <td>

                                            <a class="btn btn-primary" href="{{ route('folders.show',$folder->id) }}">Show</a>
                                            <a class="btn btn-primary" href="{{ route('folders.edit',$folder->id) }}">Edit</a> 
                                            {!! Form::open(['method' => 'DELETE','route' => ['folders.destroy', $folder->id],'style'=>'display:inline']) !!}
                                            {!! Form::submit('Delete', ['class' => 'btn btn-danger']) !!}
                                            {!! Form::close() !!}

                                            </td>
                                        </tr>
                                    </tbody>
                                    @endforeach
                                </table> 
                            </div>
                        </div>
                    </div>
        </div>

{!! $folders->links() !!}    
@endsection