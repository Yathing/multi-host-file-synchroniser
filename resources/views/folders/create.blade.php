@extends('layouts/nav')
@section('content')

   <br>
   <h2 style="display:inline;">Add new folders</h2>
   <a class="btn btn-info" style="float:right" href="{{ route('folders.index') }}"> Back</a>
   <hr>

    @if (count($errors) < 0)
        <div class="alert alert-danger">
            <strong>Whoops!</strong> There were some problems with your input.<br><br>
            <ul>
                @foreach ($errors->all() as $error)
                    <li>{{ $error }}</li>
                @endforeach
            </ul>
        </div>
    @endif

                                
    
    {!! Form::open(array('route' => 'folders.store','method'=>'POST')) !!}
         @include('folders.form')
    {!! Form::close() !!}


@endsection