@extends('layouts.nav')
@section('content')
   <br>
   <h2 style="display:inline;">Files</h2>
   <a class="btn btn-info" style="float:right" href="{{ route('files.create') }}"> Add your files</a>
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
                                            <th>File name</th>
                                            <th>Created_at</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    @foreach ($files as $file)
                                        <tr>
                                            <td class="txt-oflo">{{ $file->title}}</td>
                                            <td><span class="text-success">{{ $file->created_at}}</span></td>
                                            <td>
                                            <a class="btn btn-primary" href="{{ route('files.update',$file->id) }}">Update</a> 
                                            {!! Form::open(['method' => 'DELETE','route' => ['files.destroy', $file->id],'style'=>'display:inline']) !!}
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
{{-- {!! $files->links() !!}     --}}
@endsection