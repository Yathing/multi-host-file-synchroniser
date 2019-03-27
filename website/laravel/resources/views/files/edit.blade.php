@extends('layouts/nav')
@section('content')
<br>
<h2 style="display:inline;">Edit your file in here</h2>
<a class="btn btn-info" style="float:right" href="{{ route('files.index') }}">Back</a>
<hr>  
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            @if(session()->get('message'))
            <div class="alert alert-success">
              {{ session()->get('message') }}
            </div>
            @endif
            <div class="card">
                <div class="card-body">
                <form method="POST" action="{{ route('files.update', $file->id)}}" aria-label="{{ __('Update') }}">
                      @csrf
                      {{ csrf_field() }}
                        <div class="form-group row">
                            <label for="title" class="col-sm-4 col-form-label text-md-right">{{ __('Title') }}</label>
                            <div class="col-md-6">
                                <input id="title" type="text" class="form-control{{ $errors->has('title') ? ' is-invalid' : '' }}" name="title" value="{{ old('title') }}" required autofocus />
                                @if ($errors->has('title'))
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $errors->first('title') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="overview" class="col-sm-4 col-form-label text-md-right">{{ __('Overview') }}</label>
                            <div class="col-md-6">
                                <textarea id="overview" cols="10" rows="10" class="form-control{{ $errors->has('overview') ? ' is-invalid' : '' }}" name="overview" value="{{ old('overview') }}" required autofocus></textarea>
                                @if ($errors->has('overview'))
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $errors->first('overview') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="price" class="col-md-4 col-form-label text-md-right">{{ __('Price') }}</label>
                            <div class="col-md-6">
                                <input id="price" type="text" class="form-control{{ $errors->has('price') ? ' is-invalid' : '' }}" name="price" required>
                                @if ($errors->has('price'))
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $errors->first('price') }}</strong>
                                    </span>
                                @endif
                            </div>
                        </div>
                        <div class="form-group row mb-0">
                            <div class="col-md-8 offset-md-4">
                                <button type="submit" class="btn btn-primary">
                                    {{ __('Update') }}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


@endsection

