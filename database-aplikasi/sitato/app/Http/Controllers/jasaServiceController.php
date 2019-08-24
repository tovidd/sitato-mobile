<?php

namespace sitato\Http\Controllers;

use sitato\jasaService;
use Illuminate\Http\Request;

class jasaServiceController extends Controller
{
    public function index() 
    {
        $data= jasaService::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new jasaService;
        $data->kode_jasa_service= $request->kode_jasa_service;
        $data->nama_jasa_service= $request->nama_jasa_service;
        $data->harga_jasa_service= $request->harga_jasa_service;
        
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id) 
    {
        $data= jasaService::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(jasaService $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= jasaService::find($id);
        if(!is_null($request->kode_jasa_service))
        {
            $data->kode_jasa_service= $request->kode_jasa_service;
        }
        if(!is_null($request->nama_jasa_service))
        {
            $data->nama_jasa_service= $request->nama_jasa_service;
        }
        if(!is_null($request->harga_jasa_service))
        {
            $data->harga_jasa_service= $request->harga_jasa_service;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $data= jasaService::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
